const mongoose = require('mongoose')
const Schema = mongoose.Schema

//const DB_URL = 'mongodb://127.0.0.1:27017/repairdb';
const DB_URL = 'mongodb://127.0.0.1:27017/repairdb'

// 中心的数据结构模型
const dormitorySchema = new Schema({
    dormitory_name:String,
	dormitory_num:Number
})


// 设备详细--模型
const deviceSchema = new Schema({
    device_name:String
})

// 人员--模型  绑定了公寓信息和宿舍信息
const personSchema = new Schema({
    nick_name:String,
    account:String,
    password:String,
    permission:String,
	site_name:String,
	dormitory_name:String
})


// 维修记录——模型
const repairSchema = new Schema({
  start_time: String,
  end_time: String,   
  site_name: String,   //宿舍号
  repair_person: String,  //维修人员名称
  site_person: String,//申请维修学生名称
  dormitory_name: String, //公寓号
  device:String,   //维修项目
  repair_state:String,  //维修状态
  fix_state:String,        //维修详情
  preson_phone:String,
  repair_reverse:String
})


mongoose.set('useFindAndModify', false)
mongoose.Promise = global.Promise;
mongoose.connect(DB_URL,{useNewUrlParser: true});
const database =  mongoose.connection;
database.on('error', function(error){
  console.log('数据库repairdb连接失败：' + error)
  return
})
database.once('open', function(){
  console.log('数据库repairdb连接成功')
  // 初始化数据库
})

const db = {
  dormitory: mongoose.model('dormitorys', dormitorySchema),
  repair: mongoose.model('repair_records', repairSchema),
  device: mongoose.model('devices', deviceSchema),
  person: mongoose.model('persons', personSchema),
}

module.exports = db