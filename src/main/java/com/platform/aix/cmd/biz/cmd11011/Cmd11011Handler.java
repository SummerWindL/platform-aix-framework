package com.platform.aix.cmd.biz.cmd11011;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.config.ApisPorperties;
import com.platform.common.util.DateTimeUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * 接收终端上传的文件并保存本地
 * @author Advance
 *
 */
@Controller(Constants.MANAGE_CLIENT_REQ_URL + "/cmd_10011")
public class Cmd11011Handler extends CommandBaseHandler {

	private static final Log log = LogFactory.getLog(Cmd11011Handler.class);

	@Autowired
    private ApisPorperties apisPorperties;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd11011Request.class;
    }

	@Override
	public APIResponse execute(BaseRequest request) {

		// 转换数据实体
		Cmd11011Request uploadFileRequest = (Cmd11011Request)request;

		try {

            // yyyymmdd作为文件保存目录
            String filePath = DateTimeUtil.formatDatex(new Date());
            filePath = apisPorperties.getSaveFileLocalDir() + filePath;
            File dirFile = new File(filePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

			DiskFileItemFactory disk = new DiskFileItemFactory();
            // 设置上传文件临时保存目录
//            disk.setRepository(dirFile);
			// 设置内存可存字节数
			disk.setSizeThreshold(20 * 1024); 				
			ServletFileUpload up = new ServletFileUpload(disk);
			List<FileItem> fileItems = up.parseRequest(request.getServlet());
			
			if (fileItems.size() <= 0) {
				return new APIResponse(ResponseResult.HTTP_ERROR_UPLOAD_FILE_EMPTY);
			}

			// 一次接收一个文件
            String fileurl = null;
            for (FileItem fileItem : fileItems) {
				// 保存文件
				String fileName = fileItem.getName();
				if (!StringUtils.isEmpty(fileName)) {
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

                    //拼接上传路径。存放路径+上传的文件名
                    String fullFilePath = filePath +"\\" + fileName;

                    //构建输入输出流
                    InputStream in = fileItem.getInputStream(); //获取item中的上传文件的输入流
                    OutputStream out = new FileOutputStream(fullFilePath); //创建一个文件输出流

                    //创建一个缓冲区
                    byte b[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = -1;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))！=-1就表示in里面还有数据
                    while((len = in.read(b)) !=-1 ){  // 没数据了返回-1
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath+"\\"+filename)当中
                        out.write(b, 0, len);
                    }
                    //关闭流
                    out.close();
                    in.close();
					break;
				}
			}

//			if (StringUtils.isEmpty(fileurl)) {
//				return new APIResponse(ResponseResult.HTTP_ERROR_UPLOAD_FILE_EMPTY);
//			}
			
			// 3.返回正确结果
			Cmd11011Response data = new Cmd11011Response();
			data.setFileurl(fileurl);
			APIResponse resp = new APIResponse(ResponseResult.COMMON_SUCCESS);
			resp.setData(data);
			return resp;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new APIResponse(ResponseResult.HTTP_ERROR_UPLOAD_FILE_FAILURE);
		}
	}
//
//	/**
//	 * 取得阿里云的存储路径
//	 *
//	 * @param fileType
//	 * @param fileExtType
//	 * @param userName
//	 * @return
//	 */
//	protected String getAliyunStorePath(String appType,
//			String manuId,  String id, String fileType, String fileName) {
//		String savePath = null;
//		String strUUID = UUIDUtils.getUUID();
//		String desc = EnumFileType.getFileTypeDesc(fileType);
//		if (!StringUtils.isEmpty(desc)) {
//			savePath = appType + "/" + manuId + "/" + id + "/" +
//					desc + "/" + strUUID + "/" + fileName;
//		}
//
//		return savePath;
//	}
//
}
