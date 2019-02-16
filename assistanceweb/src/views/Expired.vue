<template>
  <section>
    <div class="expired">
      <span>您的会话已过期[<el-button type="text" @click="toLogin">重新登录</el-button>]</span>
    </div>
  </section>
</template>

<script>
export default {
  methods: {
    toLogin: function(){
      this.$router.push({path: '/login'})
    }
  }
}
</script>


<style lang="scss" scoped>
.expired{
  padding-top: 300px;
  text-align: center;
}
</style>

