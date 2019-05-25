const db = require('./db')
const bodyParser = require('body-parser');
const url = require("url");//解析url为对象
const querystring = require('querystring');//解析如‘a=1&b=2’为对象

module.exports = function (app) {
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({extended: false}));
  app.all("*", function (req, res, next) {
    next();
  });
  
    // api login
  app.get('/repair/app/login', function (req, res) {
    // 对发来的登录数据进行验证
    if (!req.query.account) {
      res.json({code: 600, msg:'name 不能为空！',data:null})
      return
    }
    if (!req.query.password) {
      res.json({code: 600, msg:'pwd 不能为空！',data:null})
      return
    }
    db.person.findOne(
      {account: req.query.account}, 
      function(err, doc){
      if (err) {
        console.log('查询出错：' + err);
      } else {
        if (!doc) {
          res.json({code: 700, msg:'不存在该用户',data:null})
          return
        } else {
          if (req.query.password != doc.password) {
            res.json({code: 700, msg:'密码不正确',data:null})
            return
          } else {
            res.json({code: 200, msg:'欢迎使用',data:doc})
            return
          }
        }

      }
    })
    // 查询数据库验证账号、密码
    // 返回登录状态
    // res.send(JSON.stringify({code: 200, data: {account: 'guojc', pass: 111111}}))
  })



  
  
  //维修记录================================================================================

  //获取指定维修记录
  app.get('/repair/app/getrepairList', function (req, res) {
    
    let repairBean = [];
    const param = {};
    const time = {};
    if (req.query.dormitory_name != "x") param.dormitory_name = req.query.dormitory_name;

	if (req.query.repair_state != "x") param.repair_state = req.query.repair_state; 
	
	if (req.query.site_name != "x") param.site_name = req.query.site_name; 
	
    if(req.query.start_time!="x") {
      param.start_time = req.query.start_time;
    }
    if(req.query.end_time!="x") {
      param.end_time = req.query.end_time;
    }
    console.log(param)
    // repair
    const getrepairBean = new Promise((resolve, reject) => {
      db.repair.find(
        param,
        {__v:0},
        function (err, doc) {
          if (err) {
            console.log('repairBean find error!')
            reject('reject repairBean')
          } else {
            if (!doc) {
              repairBean = [];
            } else {
              repairBean = doc;
            }
            resolve(repairBean)
          }
        })
    })

    const p_all = Promise.all([getrepairBean])

    p_all.then((suc) => {
      let data = suc[0]
     
      res.json({ code: 200, msg: '查询成功', data: data })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: data })
      return
    })
  })
  
  //获取全部维修记录
  
  app.get('/repair/app/getrepairallList', function (req, res) {
    
    let repairBean = [];
    const param = {};
    const time = {};
    
    
    // repair
    const getrepairBean = new Promise((resolve, reject) => {
      db.repair.find(
        param,
        {__v:0},
        function (err, doc) {
          if (err) {
            console.log('repairBean find error!')
            reject('reject repairBean')
          } else {
            if (!doc) {
              repairBean = [];
            } else {
              repairBean = [];
            }
            resolve(repairBean)
          }
        })
    })

    const p_all = Promise.all([getrepairBean])

    p_all.then((suc) => {
      
      res.json({ code: 200, msg: '查询成功', data: suc[0] })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: err })
      return
    })
  })
  
  

  //删除维修记录————完成
  app.get('/repair/app/deleterepairRecord', function (req, res) {
      console.log(req.query)
      db.repair.findByIdAndRemove(
        req.query._id,
        function(err,doc){
          if(err){
            console.log(err)
            res.json({code:700,msg:'删除失败'})
          }else{
            res.json({code:200,msg:'删除成功'})
          }
        }
      )
  })

  //修改维修记录————完成
  app.post('/repair/app/changerepairRecord', function (req, res) {
    console.log(req.body)
    db.repair.findByIdAndUpdate(
      req.body._id,
      req.body,
      {upsert:true},
      function(err,doc){
        if (err) {
          console.log('修改错误：' + err);
          res.json({code: 700, msg:'修改失败'})
          return
        } else{
          res.json({code: 200, msg:'修改成功'})
        }
      }
    )
  })

  //增加维修记录————完成      前端添加时需要传入所有参数 无输入的参数设为空
  app.post('/repair/app/addrepairRecord', function (req, res) {
    db.repair.create(
      req.body,
      function(err,doc){
        if(err){
          console.log('添加失败' + err);
          res.json({code: 700, msg:'添加失败'})
        } else{
          console.log(doc);
          res.json({code: 200, msg:'添加成功'})
        }
      }
    )

  })

  //申请维修记录=====================================================
  //获取申请维修列表————完成
  app.get('/repair/app/getRequireList', function (req, res) {
    
    let repairBean = [];
    const param = {};
 
    param.repair_state= '未维修';
    const getrepairBean = new Promise((resolve, reject) => {
      db.repair.find(
        param,
        {__v:0},
        function (err, doc) {
          if (err) {
            console.log('repairBean find error!')
            reject('reject repairBean')
          } else {
            if (!doc) {
              repairBean = [];
            } else {
              repairBean = doc;
            }
            resolve(repairBean)
          }
        })
    })

    const p_all = Promise.all([getrepairBean])

    p_all.then((suc) => {
      
      res.json({ code: 200, msg: '查询成功', data: suc[0] })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: err })
      return
    })
  })

  //获取公寓列表
  app.get('/repair/app/getCenterList', function (req, res) {
    let DormitoryBean = [];

    const getDormitory = new Promise((resolve, reject) => {
      db.dormitory.find(
        {},
        function (err, doc) {
          if (err) {
            console.log('dormitory find error!');
            reject('reject dormitory')
          } else {
            if (!doc) {
              DormitoryBean = [];
            } else {
              DormitoryBean = doc;
            }
            resolve(DormitoryBean)
          }
        })
    })

    const p_all = Promise.all([getDormitory])

    p_all.then((suc) => {
    
      res.json({ code: 200, msg: '查询成功', data: suc[0] })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: err })
      return
    })

  })



  //人员管理=======================================
  //查询人员————完成
  app.get('/repair/app/getPersonList', function (req, res) {
    
    let PersonBean = [];
    const param = {};

	if(req.query.nick_name!=undefined) {
      param.nick_name = req.query.nick_name;
    }
	
    const getpersonBean = new Promise((resolve, reject) => {
      db.person.find(
        param,
        {__v:0},
        function (err, doc) {
          if (err) {
            console.log('personBean find error!')
            reject('reject personBean')
          } else {
            if (!doc) {
              personBean = [];
            } else {
              personBean = doc;
            }
            resolve(personBean)
          }
        })
    })

    const p_all = Promise.all([getpersonBean])

    p_all.then((suc) => {
      let data =  suc[0]
      
      res.json({ code: 200, msg: '查询成功', data: data })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: err })
      return
    })
  })
  //增加人员————完成
  app.post('/repair/app/addPerson', function (req, res) {
    db.person.create(
      req.body,
      function(err,doc){
        if(err){
          res.json({code:700,msg:'添加失败'})
        }else{
          res.json({code:200, msg:'添加成功'})
        }
      }
      )
  })
  //删除人员————完成
  app.get('/repair/app/deletePerson', function (req, res) {
    db.person.findByIdAndRemove(
      req.query._id,
      function(err,doc){
        if(err){
          res.json({code:700,msg:'删除失败'})
        }else{
          res.json({code:200,msg:'删除成功'})
        }
      }
    )
  })
  //修改人员————完成
  app.post('/repair/app/changePerson', function (req, res) {
    console.log(req.body)
    db.person.findByIdAndUpdate(
      req.body._id,
      req.body,
      {upsert:true},
      function(err,doc){
        if (err) {
          console.log('修改错误：' + err);
          res.json({code: 700, msg:'修改失败：'})
          return
        } else{
          res.json({code: 200, msg:'修改成功'})
        }
      }
    )
  })

  
  //设备详细=====================================完成了！！！
  //查询设备详细————完成
  app.get('/repair/app/getDeviceList', function (req, res) {
    let deviceBean = [];

    const getDevice = new Promise((resolve, reject) => {
      db.device.find(
        {},
        function (err, doc) {
          if (err) {
            console.log('device find error!');
            reject('reject device')
          } else {
            if (!doc) {
              deviceBean = [];
            } else {
              deviceBean = doc;
            }
            resolve(deviceBean)
          }
        })
    })

    const p_all = Promise.all([getDevice])

    p_all.then((suc) => {
      
      res.json({ code: 200, msg: '查询成功', data: suc[0] })
      return
    }).catch((err) => {
      console.log('err all:' + err)
      res.json({ code: 600, msg: '查询出错', data: err })
      return
    })

  })
  
  
  //增加设备详细————完成
  app.get('/repair/app/addDevice', function (req, res) {
    db.device.create(
      {device_name:req.query.device_name},
      function(err,doc){
        if(err){
          res.json({code:700,msg:'添加失败'})
        }else{
          res.json({code:200, msg:'添加成功'})
        }
      }
    )
  })
  //删除设备详细————完成
  app.get('/repair/app/deleteDevice', function (req, res) {
    db.device.findByIdAndRemove(
      req.query._id,
      function(err,doc){
        if(err){
          res.json({code:700,msg:'删除失败'})
        }else{
          res.json({code:200,msg:'删除成功'})
        }
      }
    )
  })

  app.get('*', function (req, res) {
    res.end('404')
  })

}