@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul
title Gitee Release上传工具

:: ---------- 执行 Maven 打包 ----------
echo.
echo 正在执行 Maven 打包...
call mvn clean -s D:\work\2_workspace\my-project\platform-aix-framework\settings-local.xml package -DskipTests
if errorlevel 1 (
    echo [错误] Maven 打包失败！请检查日志并重试。
    pause
    exit /b 1
)
echo Maven 打包完成！

:: ---------- 默认配置参数 ----------
set DEFAULT_TOKEN=65ca1b8f3ac059373ca69bea2778f27c
set DEFAULT_TAG=v1.0
set TARGET_BRANCH=dev
set GITEE_USER=winasaser
set REPO_NAME=platform-aix-framework
set JAR_PATH=D:\work\2_workspace\my-project\platform-aix-framework\target\aix-platform.jar
set OUTPUT_DIR=D:\work\2_workspace\my-project\platform-aix-framework\upload

:: ---------- 交互式输入带默认值 ----------
:input_token
echo.
echo 请输入Gitee私人令牌（默认值：%DEFAULT_TOKEN%）。按回车键使用默认值。
set "ACCESS_TOKEN="
set /p ACCESS_TOKEN="请输入："
if "%ACCESS_TOKEN%"=="" set "ACCESS_TOKEN=%DEFAULT_TOKEN%"
if not defined ACCESS_TOKEN goto input_token

:input_tag
echo.
echo 请输入Release版本标签（默认值：%DEFAULT_TAG%）。按回车键使用默认值。
set "INPUT_TAG="
set /p INPUT_TAG="请输入："
if "%INPUT_TAG%"=="" (set "TAG_NAME=%DEFAULT_TAG%") else (set "TAG_NAME=%INPUT_TAG%")

:: ---------- 配置开关 ----------
set DEBUG_MODE=false
set DELETE_OLD_RELEASE=true

:: ---------- 初始化清理 ----------
echo.
echo 正在初始化环境...
if exist "%OUTPUT_DIR%" (
  echo [清理] 删除旧输出目录: %OUTPUT_DIR%
  rmdir /s /q "%OUTPUT_DIR%" || (
    echo [错误] 删除目录失败！
    pause
    exit /b 1
  )
)
mkdir "%OUTPUT_DIR%" 2>nul || (
  echo [错误] 创建目录失败！
  pause
  exit /b 1
)
if %DEBUG_MODE%==true pause

:: ---------- 步骤1：创建分卷压缩包 ----------
echo.
echo [1/3] 正在创建分卷压缩包...
cd /d "%OUTPUT_DIR%" || (
  echo [错误] 进入目录失败！
  pause
  exit /b 1
)
where 7z >nul 2>&1 || (
  echo [错误] 请先安装7-Zip并添加至PATH！
  pause
  exit /b 1
)
7z a -v50m -tzip "aix-platform_split.zip" "%JAR_PATH%"
if errorlevel 1 (
  echo [错误] 压缩失败，错误码: %errorlevel%
  pause
  exit /b 1
)
if %DEBUG_MODE%==true (
  echo 压缩完成，分卷文件列表：
  dir /b
  pause
)

:: ---------- 步骤2：处理Release ----------
echo.
echo [2/3] 处理Gitee Release...

:: 删除旧Release（如果存在）
if %DELETE_OLD_RELEASE%==true (
  echo 正在检查旧Release...
  curl -s ^
    -H "Authorization: token %ACCESS_TOKEN%" ^
    "https://gitee.com/api/v5/repos/%GITEE_USER%/%REPO_NAME%/releases/tags/%TAG_NAME%" ^
    > old_release.json

  for /f "tokens=2 delims=:," %%i in ('type old_release.json ^| findstr /C:"\"id\""') do (
    set "OLD_RELEASE_ID=%%i"
    set "OLD_RELEASE_ID=!OLD_RELEASE_ID:"=!"
    set "OLD_RELEASE_ID=!OLD_RELEASE_ID: =!"
  )
  if defined OLD_RELEASE_ID (
    echo 发现旧Release ID: !OLD_RELEASE_ID!，正在删除...
    curl -X DELETE ^
      -H "Authorization: token %ACCESS_TOKEN%" ^
      "https://gitee.com/api/v5/repos/%GITEE_USER%/%REPO_NAME%/releases/!OLD_RELEASE_ID!" && (
      echo   成功删除旧Release
    ) || (
      echo   [警告] 删除失败，可能已不存在
    )
    del old_release.json
  )
)

:: 创建新Release（适配target_commitish参数）
echo.
echo 正在创建新Release: %TAG_NAME%
curl -X POST ^
  -w "\nHTTP_STATUS=%%{http_code}\n" ^
  -H "Content-Type: application/json" ^
  -H "Authorization: token %ACCESS_TOKEN%" ^
  -d "{\"tag_name\":\"%TAG_NAME%\",\"name\":\"Release %TAG_NAME%\",\"body\":\"Auto-generated release\",\"target_commitish\":\"%TARGET_BRANCH%\"}" ^
  "https://gitee.com/api/v5/repos/%GITEE_USER%/%REPO_NAME%/releases" ^
  > release_response.json

:: 验证HTTP状态码
for /f "tokens=2 delims==" %%a in ('type release_response.json ^| findstr /b /c:"HTTP_STATUS="') do (
  set "HTTP_STATUS=%%a"
)
if not "!HTTP_STATUS!"=="201" (
  echo [错误] 创建Release失败，HTTP状态码: !HTTP_STATUS!
  type release_response.json
  pause
  exit /b 1
)

:: 强化ID提取（直接解析JSON字段）
set "RELEASE_ID="
for /f "tokens=2 delims=:," %%i in ('type release_response.json ^| findstr /C:"\"id\""') do (
  set "RELEASE_ID=%%i"
  set "RELEASE_ID=!RELEASE_ID:"=!"
  set "RELEASE_ID=!RELEASE_ID: =!"
)
if "!RELEASE_ID!"=="" (
  echo [错误] 无法解析Release ID!
  type release_response.json
  pause
  exit /b 1
)
echo 成功创建Release，ID: !RELEASE_ID!
if %DEBUG_MODE%==true pause

:: ---------- 步骤3：上传分卷 ----------
echo.
echo [3/3] 上传分卷文件到附件...
set /a file_count=0
for %%f in ("%OUTPUT_DIR%\aix-platform_split.zip.*") do (
  set /a file_count+=1
  echo 正在上传第!file_count!个分卷: %%~nxf
  curl -X POST ^
    -H "Authorization: token %ACCESS_TOKEN%" ^
    -F "file=@%%f" ^
    "https://gitee.com/api/v5/repos/%GITEE_USER%/%REPO_NAME%/releases/!RELEASE_ID!/attach_files" && (
    echo   √ 上传成功
  ) || (
    echo   [错误] 分卷上传失败！
    pause
    exit /b 1
  )
)

:: ---------- 最终输出 ----------
echo.
echo --------------------------------------
echo -                                    -
echo -        所有分卷上传完成！          -
echo -                                    -
echo --------------------------------------
echo 访问地址：https://gitee.com/%GITEE_USER%/%REPO_NAME%/releases/%TAG_NAME%
pause