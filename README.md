# platform-aix-framework
前后端分离 后台开发框架

## 后台接口

springboot + jetty + springdatajpa

若启动存在异常多半是包依赖的问题，去到对应的目录删除包重新maven update就好

可自行实现业务逻辑


e.g 
![eg1](https://github.com/SummerWindL/imgrepository/blob/master/platform-aix/application-json.png) 

请求地址（可配置）：```http://localhost:16089/aixapigateway/api/v1/manageclient/cmd_10010factoryid=zav6f0t3w6fwz9u85remvqxkduo99tjj&factorysecretkey=3fpjfimcp2iybd6h4l30c1kpvjzfcipg```

数据请求采用Web  Post请求的方式,上传和回应数据为JSON格式，在接收页面处理上传的数据即可

application/json
application/x-www-form-urlencoded
两者都支持

请求JSON  
```json 
{"DoctorName":"医生名称","MachineId":"HST821255555","BloodPressure":{"HighPressure":"96","Pulse":"65","LowPressure":"57","Result":""},"Temperature":{"Temperature":"37","Result":"1"},"Chol":{"Chol":"2.77","Result":"1"},"Urinalysis":{"BIL":0,"PRO":0,"VC":0,"CR":0,"Result":0,"GLU":0,"UCA":0,"URO":0,"SG":0,"PH":0,"NIT":0,"MAL":0,"BLD":0,"KET":0,"LEU":0},"Cardiovascular":{"CMBV":"","HeartFunction1":"","SV":"","HeartFunction2":"","TPR":"","VascularCondition1":"","VascularCondition2":"","CO":"","HOV":"","N":"","Result":"","PAWP":""},"Lung":{"PEF":5.77,"FEF25":5.69,"FVC":3.6,"Lung":"1112","FEV1":3.25,"FEF2575":4.02,"FEF75":2.16,"Result":"0"},"Alcohol":{"errcode":"","AlcoholImg":"null","errinfo":"","Alcohol":"2.0","Result":"0"},"BloodSugar":{"BloodsugarType":"1","BloodSugar":"3.8","Result":"1"},"UnitNo":"4450585525","DoctorId":"医生工号","PEEcg":{"QTc":"","PR":"","QT":"","QRSAxis":"","Hr":"","TAxis":"","SV1":"","Result":"","RV5":"","QRS":"","PAxis":"","EcgData":"","EcgImg":""},"ArteryAve":{"SBP":"0","CSBP":"0","CAPP":"0","DBP":"0","AVI":"0","Pulse":"0","API":"0"},"MacAddr":"00-06-55-55-0A-2B","MinFat":{"Shape":"3","Height":"182","FatRate":"17","Physique":"3","BasicMetabolism":"1157","Weight":"80","Bmi":"24.4","Result":"1"},"BMD":{"PAB":"","TSCORE":"2.0","SOS":"300","EOA":"","RRF":"","YOUNG_ADULT":"","OI":"","BQI":"","ZSCORE":"2.2","AGE_MATCHED":"","BUA":"","Result":""},"Height":{"Height":"180","IdealWeight":"74","Weight":"70","BMI":"22","Result":"2"},"Hb":{"Hct":"6.0","Hb":"5.0","Result":"0"},"LoginType":"1","Member":{"Nation":"","IcCode":"","Address":"广东省XX市","Sex":"1","BarCode":"","Birthday":"1989-09-28","SocialCode":"","Mobile":"15811397368","EndDate":"","Name":"陈XX ","StartDate":"","Department":"","UserID":"","UserIcon":"","IdCode":"420102199003078071","Age":"25"},"Whr":{"Waistline":"22","Whr":"25","Hipline":"88","Result":"1"},"UnitName":"XX医院","RecordNo":"2125555520150409143202","Ecg":{"nGain":"2","Analysis":"","Hr":"88","EcgData":"","Result":""},"Ua":{"Ua":"0.54","Result":"1"},"Bo":{"Oxygen":"99","BpmList":"","EndTime":"2015-04-09 14:32:02","SecondCount":"","OxygenList":"","StartTime":"2015-04-09 14:32:02","Bpm":"","Result":""},"DeviceType":"SK-T8","Fat":{"Water":"26.8","YoutuiFat":"3.0","Minerals":"1.2","Result":"","FatAdjust":"-0.3","ZuotuiMuscle":"9.7","Viscera":"7","YoutuiMuscle":"9.9","Bmc":"3.1","YoubiMuscle":"3.1","WaterRate":"36.4","Muscle":"34.4","Foc":"9","MuscleAdjust":"0.4","ZuobiMuscle":"2.9","MuscleRate":"75.2","QuganMuscle":"30.9","ExceptFat":"49.8","Fic":"17.8","WeightAdjust":"0.1","YoubiFat":"0.7","QuganFat":"8.0","Protein":"7.6","Fat":"26.2","FatRate":"16","ZuotuiFat":"3.0","ZuobiFat":"0.7","BasicMetabolism":"1140"},"MeasureTime":"2015-04-09 14:32:02","BloodFat":{"HdlChol":"3.56","CalcLdl":"2.35","TChol":"2.77","Trig":"4.21","TcHdl":"","Result":"1"}}
```
postman接口调试

