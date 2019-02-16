<template>
  <el-container class="container">
    <el-header class="header">
      <div class='logo' :class="'logo-width'">
        <span>{{sysName}}</span>
      </div>
      <div class="tools">
        <i class="fa fa-align-justify" @click="collapse"></i>
      </div>
      <div class="userinfo">
        <el-dropdown>
          <span class="el-dropdown-link userinfo-inner"><img :src="sysUserAvatar"/>{{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="setting" v-hasPermission="'system.setting'">个人设置</el-dropdown-item>
            <el-dropdown-item @click.native="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container class="main">
      <el-aside class="asider" :width="asideWidth">
        <el-menu default-active="$route.path" class="el-menu-vertical-demo" unique-opened router
        @open="handleOpen" @close="handleClose" @select="handleSelect" :collapse="collapsed">
          <template v-for="(item, index) in $router.options.routes">
            <template v-if="item.meta.isMenu">
              <el-submenu :index="item.path" v-if="!item.meta.isLeaf" :key="index"  v-hasPermission="item.meta.permission">
                <template slot="title">{{item.meta.title}}</template>
                <template v-for="(child, index) in item.children">
                  <el-menu-item :index="child.path" :key="index" v-if="child.meta.isMenu"  v-hasPermission="child.meta.permission">{{child.meta.title}}</el-menu-item>
                </template>
              </el-submenu>
              <el-menu-item :index="item.path" :key="index" v-else v-hasPermission="item.meta.permission">{{item.meta.title}}</el-menu-item>
            </template>
          </template>
        </el-menu>
      </el-aside>
      <el-main class="content-container">
        <el-col :span="24" class="breadcrumb-container">
          <strong class="title">{{$route.meta.title}}</strong>
        </el-col>
        <el-col :span="24" class="content-wrapper">
          <transition name="fade" mode="out-in">
            <div>
              <keep-alive>
                <router-view v-if="$route.meta.keepAlive"></router-view>
              </keep-alive>
              <router-view v-if="$route.meta.keepAlive == undefined"></router-view>
            </div>
          </transition>
        </el-col>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data(){
    return {
      sysName:'助销小工具',
      collapsed: false,
      asideWidth: '230px'
    }
  },
  methods: {
    handleOpen(key, keyPath){

    },
    handleClose(key, keyPath){

    },
    handleSelect(key, keyPath){

    },
    collapse: function(){
      this.collapsed=!this.collapsed;
      if(this.collapsed){
        this.asideWidth = '60px'
      }else{
        this.asideWidth = '230px'
      }
    },
    logout: function(){
      this.$confirm('确认退出吗？','提示',{}).then(()=>{
        this.$ajax.post('/logout').then((res)=>{
          if(res.success){
            this.$store.commit('logout')
            this.$router.push({path: '/login'})
          }
        })
      })
    },
    setting: function(){
      this.$router.push('/system/setting')
    }
  },
  computed: {
    sysUserName: function(){
      return this.$store.getters.getUser.nickName
    },
    sysUserAvatar: function(){
      let avatar = this.$store.getters.getUser.imageUrl
      if(avatar==null || avatar=='' || avatar == undefined){
        avatar = '/static/user.png'
      }
      return avatar
    }
  }
}
</script>

<style scoped lang="scss">
	@import '~scss_vars';

  .container{
    top: 0px;
		bottom: 0px;
		width: 100%;
    height: 100%;
    .header{
      background: $color-primary;
      line-height: 60px;
      padding: 0px;
      color:#fff;
      display: flex;
      flex-wrap: nowrap;
      justify-content: space-between;
      .logo{
        font-size: 22px;
        span{
          padding-left: 20px;
        }
      }
      .logo-width{
        width: 230px;
      }
      .tools{
        cursor: pointer;
        padding: 0px 20px;
        line-height: 60px;
        flex-grow: 1;
        i{
          padding-right: 10px;
          margin: auto;
        }
      }
      .userinfo{
        text-align: right;
        padding-right: 20px;
        .userinfo-inner{
          cursor: pointer;
          color:#fff;
          img{
            float: right;
            width: 40px;
            border-radius: 20px;
            height: 40px;
            margin: 10px 0px 10px 10px;
          }
        }
      }
    }
    .main{
      overflow: hidden;
      height: 100%;
      .asider{
        .el-menu-vertical-demo{
          height: 100%;
          background-color: #eef1f6;
          .el-menu{
            .el-menu-item{
              background-color: #e4e8f1;
              .el-submenu__title{
                color: #48576a;
                font-size: 14px;
                transition: border-color .3s,background-color .3s,color .3s;
              }
            }
          }
        }
      }
      .menu-collapsed{
        width: 60px;
      }
      .menu-expanded{
        width: 230px;
      }
      .content-container{
        padding: 20px;
        overflow-y: scroll;
        width: 100%;
        .breadcrumb-container{
          margin-bottom: 15px;
          .title{
            width: 200px;
            color: #475669;
            float: left;
          }
          .breadcrumb-inner{
            float: right;
          }
        }
        .content-wrapper{
          background-color: #fff;
          box-sizing: border-box;
        }
      }
    }
  }
</style>
