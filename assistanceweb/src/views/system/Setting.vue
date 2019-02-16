<template>
  <section>
    <div class="setting-box">
      <div class="avatar-box">
        <el-upload
          list-type="avatar-uploader"
          :action="action"
          :show-file-list="false"
          :on-success="handleAddSuccess"
          :data="{type: 'image', size: '150'}">
          <img v-if="user.imageUrl" :src="user.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <div class="form-box">
        <el-row>
          <span class="lable">用户名:</span>
          <span class="value">{{user.userName}}</span>
          <el-button type="text" class="btn" @click="handleModifyPassword">修改密码</el-button>
        </el-row>
        <el-row>
          <span class="lable">昵称:</span>
          <span class="value">{{user.nickName}}</span>
          <el-button type="text" class="btn" @click="handleNickName">修改</el-button>
        </el-row>
        <el-row>
          <span class="lable">姓名:</span>
          <span class="value">{{user.realName}}</span>
          <el-button type="text" class="btn" v-if="user.realName==undefined || user.realName==null || user.realName==''" @click="handleRealName">去实名</el-button>
        </el-row>
        <el-row>
          <span class="lable">手机号:</span>
          <span class="value">{{user.phone}}</span>
          <el-button type="text" class="btn" v-if="user.phone==undefined || user.phone==null || user.phone==''" @click="handlePhone">绑定手机</el-button>
          <el-button type="text" class="btn" v-else @click="handlePhone">更换手机</el-button>
        </el-row>
      </div>
    </div>

		<!--修改密码界面-->
		<el-dialog title="修改密码" :visible.sync="pwdFormVisible" :close-on-click-modal="false">
			<el-form :model="pwdForm" label-width="80px" :rules="pwdFormRules" ref="pwdForm">
				<el-form-item label="原密码" prop="oldpwd">
					<el-input type="password" v-model="pwdForm.oldpwd" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="密码" prop="newpwd">
					<el-input type="password" v-model="pwdForm.newpwd" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="确认密码" prop="secondpwd">
					<el-input type="password" v-model="pwdForm.secondpwd" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="pwdFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="pwdSubmit" :loading="pwdLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--绑定或更换手机界面-->
		<el-dialog :title="title" :visible.sync="phoneFormVisible" :close-on-click-modal="false">
			<el-form :model="phoneForm" label-width="80px" :rules="phoneFormRules" ref="phoneForm">
				<el-form-item label="手机号" prop="phone">
					<el-input v-model="phoneForm.phone" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="phoneFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="phoneSubmit" :loading="phoneLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--实名界面-->
		<el-dialog title="实名" :visible.sync="realNameFormVisible" :close-on-click-modal="false">
			<el-form :model="realNameForm" label-width="80px" :rules="realNameFormRules" ref="realNameForm">
				<el-form-item label="姓名" prop="realName">
					<el-input v-model="realNameForm.realName" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="realNameFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="realNameSubmit" :loading="realNameLoading">提交</el-button>
			</div>
		</el-dialog>

    <!--修改昵称界面-->
		<el-dialog title="修改昵称" :visible.sync="nickNameFormVisible" :close-on-click-modal="false">
			<el-form :model="nickNameForm" label-width="80px" :rules="nickNameFormRules" ref="nickNameForm">
				<el-form-item label="昵称" prop="nickName">
					<el-input v-model="nickNameForm.nickName" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="nickNameFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="nickNameSubmit" :loading="nickNameLoading">提交</el-button>
			</div>
		</el-dialog>
  </section>
</template>

<script>
export default {
  data(){
    return {
      user: {
        userName: '',
        nickName: '',
        phone: '',
        realName: '',
        imageUrl: ''
      },
      service: '',

      pwdFormRules: {
        oldpwd: [
					{ required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newpwd: [
					{ required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        secondpwd: [
					{ required: true, message: '请确认密码', trigger: 'blur' }
				]
      },
      pwdForm: {
        newpwd: '',
        oldpwd: '',
        secondpwd: ''
      },
      pwdFormVisible: false,
      pwdLoading: false,

      phoneFormRules: {
        phone: [
					{ required: true, message: '请输入手机号', trigger: 'blur' }
        ]
      },
      phoneForm: {
        phone: ''
      },
      phoneFormVisible: false,
      phoneLoading: false,
      title: '绑定手机',

      realNameFormRules: {
        realName: [
					{ required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      realNameForm: {
        realName: ''
      },
      realNameFormVisible: false,
      realNameLoading: false,

      nickNameFormRules: {
        nickName: [
					{ required: true, message: '请输入昵称', trigger: 'blur' }
        ]
      },
      nickNameForm: {
        nickName: ''
      },
      nickNameFormVisible: false,
      nickNameLoading: false
    }
  },
  methods: {
    handleAddSuccess: function(response, file, fileList){
      if(response.success){
        this.submitSetting({imageUrl: this.service+response.data[0]})
      }
    },
    submitSetting: function(setting){
      let param = Object.assign({}, setting)
      param.id = this.user.id
      return this.$ajax.post("/system/user/setting/save", param).then(res=>{
        if(res.success){
          this.$message.success('保存成功')
          let user = this.$store.getters.getUser
          for(let key in setting){
            user[key] = setting[key]
            this.user[key] = setting[key]
          }
          this.$store.commit('setUser', user)
          return Promise.resolve(1)
        }
        return Promise.reject(0)
      })
    },
    pwdSubmit: function(){
      this.$refs.pwdForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认修改密码吗？', '提示', {}).then(() => {
            this.pwdLoading = true
            this.submitSetting({password: this.pwdForm.newpwd}).then(res=>{
              this.pwdLoading = false
              this.pwdFormVisible = false
            })
          })
        }
      })
    },
    handleModifyPassword: function(){
      this.pwdFormVisible = true
    },
    handlePhone: function(){
      if(this.user.phone==undefined || this.user.phone==null || this.user.phone==''){
        this.title = '绑定手机'
      }else{
        this.title = '更换手机'
        this.phoneForm.phone = this.user.phone
      }
      this.phoneFormVisible = true
    },
    phoneSubmit: function(){
      this.$refs.phoneForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.phoneLoading = true
            this.submitSetting({phone: this.phoneForm.phone}).then(res=>{
              this.phoneLoading = false
              this.phoneFormVisible = false
            })
          })
        }
      })
    },
    handleRealName: function(){
      this.realNameForm.realName = this.user.realName
      this.realNameFormVisible = true
    },
    realNameSubmit: function(){
      this.$refs.realNameForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认实名吗？', '提示', {}).then(() => {
            this.realNameLoading = true
            this.submitSetting({realName: this.realNameForm.realName}).then(res=>{
              this.realNameLoading = false
              this.realNameFormVisible = false
            })
          })
        }
      })
    },
    handleNickName: function(){
      this.nickNameForm.nickName = this.user.nickName
      this.nickNameFormVisible = true
    },
    nickNameSubmit: function(){
      this.$refs.nickNameForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认修改昵称吗？', '提示', {}).then(() => {
            this.nickNameLoading = true
            this.submitSetting({nickName: this.nickNameForm.nickName}).then(res=>{
              this.nickNameLoading = false
              this.nickNameFormVisible = false
            })
          })
        }
      })
    }
  },
  mounted(){
    this.service = this.$imageService
    this.user = this.$store.getters.getUser
    if(this.user.imageUrl == '' || this.user.imageUrl == null || this.user.imageUrl == undefined){
      this.user.imageUrl = '/static/user.png'
    }
  },
  computed: {
    action: function(){
      return this.service + "/image/up"
    }
  }
}
</script>

<style lang="scss" scoped>
.setting-box{
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  margin: 30px 0px 0px 60px;
  .avatar-box{
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
  }
  .form-box{
    margin-left: 20px;
    .lable{
      display: inline-block;
      width: 60px;
      text-align: right;
    }
    .value{
      padding-left: 5px;
    }
    .el-row{
      line-height: 30px;
    }
    .btn{
      margin-left: 10px;
    }
  }
}
</style>

