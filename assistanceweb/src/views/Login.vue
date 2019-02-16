<template>
  <section>
    <div class="top">
      <img src="/static/logo.png" class="logo"><b></b>
    </div>
    <div class="middle">
      <div class="login-container">
        <div class="login-tabs">
          <div class="login-tab">
            <div class="login-tab-r">账号登录</div>
          </div>
        </div>
        <el-form :model="user" :rules="rules" ref="loginForm" label-position="left" label-width="0px" class="login-form">
          <el-form-item prop="userName" class="field">
            <el-input type="text" v-model="user.userName" auto-complete="off" placeholder="用户名" class="field-input">
              <template slot="prepend">
                <label class="field-lable"></label>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password" class="field">
            <el-input type="password" v-model="user.password" auto-complete="off" placeholder="密  码" class="field-input">
              <template slot="prepend">
                <label class="field-lable password-lable"></label>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item class="field submit-field">
            <el-button type="danger"  @click.native.prevent="login" :loading="logining">登&nbsp;&nbsp;&nbsp;&nbsp;录</el-button>
          </el-form-item>
        </el-form>
        <div class="coagent">
          
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  export default {
    data() {
      return {
        logining: false,
        user: {
          userName: '',
          password: '',
        },
        rules: {
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      reset() {
        this.$refs.loginForm.resetFields();
      },
      login(ev) {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.logining = true;
            let loginUser = {
              userName: this.$util.encrypt(this.user.userName, this.$store.getters.getKey),
              password: this.$util.encrypt(this.user.password, this.$store.getters.getKey)
            }
            this.$ajax.post('/login',loginUser).then(res=>{
              this.logining = false
              if(res.success){
                this.$store.commit('login')
                Promise.all([this.$ajax.get('/currUser').then((r1)=>{
                  if(r1.success){
                    this.$store.commit('setUser', r1.data)
                  }else{
                    return Promise.reject(-1)
                  }
                }),this.$ajax.get('/currUserPermission').then((r2)=>{
                  if(r2.success){
                    this.$store.commit('setPermissions', r2.data)
                  }else{
                    return Promise.reject(-2)
                  }
                })]).then((r3)=>{
                  this.$router.push({path: '/admin'})
                }).catch(function(){
                  this.$store.commit('logout')
                })
              }
            })
          }
        });
      }
    },
    created(){
      this.$ajax.get('/key').then(r=>{
        if(r.success){
          let key = this.$util.random(8)
          this.$ajax.post('/key',{key: this.$util.rsaEncrypt(key, r.data)}).then(k=>{
            if(k.success){
              this.$store.commit('setKey', key)
            }
          })
        }
      })
    }
  }

</script>

<style lang="scss">
.top{
  height: 44px;
  padding: 22px 0px;
  width: 1190px;
  margin: auto;
  .logo{
    width: 270px;
    margin-top: 8px;
  }
  b{
    display: inline-block;
    width: 110px;
    height: 30px;
    padding-top: 4px;
    background: url(/static/l-icon.png) no-repeat;
  }
}
.middle{
  background: url(/static/bg.jpg) no-repeat;
  background-position: center center;
  background-size: cover;
  min-height: 600px;
  position: relative;
  .login-container {
    width: 346px;
    background: #fff;
    font-family: 'Microsoft YaHei';
    position: absolute;
    top: 130px;
    right: 10vh;
    .login-tabs{
      display: flex;
      margin-bottom: 23px;
      .login-tab{
        height: 54px;
        font-size: 18px;
        text-align: center;
        border-bottom: 1px solid #f4f4f4;
        background: #fff;
        width: 346px;
        .login-tab-l{
          width: 99%;
          border-right: 1px solid #f4f4f4;
        }
        .login-tab-l,.login-tab-r{
          line-height: 18px;
          margin-top: 18px;
        }
        .login-tab-l:hover,.login-tab-r:hover,.login-tab-n{
          font-weight: 700;
          color: #e4393c;
        }
      }
    }
    .login-form{
      padding: 20px;
      .field{
        margin-bottom: 20px;
        .field-input .el-input__inner{
          border: 1px solid #bdbdbd;
          border-left: none;
          border-radius: 0px;
        }
        .field-lable{
          width: 38px;
          height: 38px;
          display: block;
          background: url(/static/pwd-icons-new.png)
        }
        .user-name-lable{
          background-position: 0 0;
        }
        .password-lable{
          background-position: -48px 0;
        }
        .el-input-group__prepend{
          border: 1px solid #bdbdbd;
          padding: 0px;
        }
      }
      .submit-field{
        margin-bottom: 10px;
        .el-button{
          padding: 8px 15px;
          display: block;
          width: 100%;
          background: #e4393c;
          border: 1px solid #e85356;
          font-size: 20px;
          border-radius: 0px;
        }
        .el-form-item__content{
          line-height: 31px;
        }
      }
    }
    .qrcode-form{
      .qrcode-main{
        height: 168px;
        display: flex;
        justify-content: center;
        .qrcode-img{
          border: 1px solid #f4f4f4;
          padding: 6px;
          width: 157px;
        }
      }
      .qrcode-panel{
        width: 257px;
        text-align: center;
        margin: 18px auto 14px;
        color: #666;
        font-size: 8px;
        ul{
          list-style: none;
          padding: 0;
          margin: 0;
          li{
            display: inline-block;
            padding: 0 0 0 5px;
            margin: 0 5px 0 0;
            .red{
              color: #e4393c;
            }
          }
        }
      }
    }
    .coagent{
      padding-left: 20px;
      padding-right: 20px;
      line-height: 50px;
      border-top: 1px solid #f4f4f4;
      height: auto;
      background-color: #fcfcfc;
      display: flex;
      .regist-link{
        text-align: right;
        width: 100%;
        a{
          text-decoration: none;
          color: #b61d1d;
        }
        b{
          display: inline-block;
          width: 16px;
          height: 16px;
          overflow: hidden;
          background: url(/static/pwd-icons-new.png) -104px -75px no-repeat;
          vertical-align: middle;
          margin-right: 5px;
        }
      }
    }
  }
}
</style>
