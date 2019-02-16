<template>
  <section>
    <div class="welcome">{{welcome}}</div>
  </section>
</template>

<script>
export default {
  data(){
    return {
      welcome: ''
    }
  },
  methods: {
    
  },
  created(){
    this.$ajax.get('/index').then(res=>{
      if(res.success){
        this.welcome = res.data || '欢迎使用'
      }else{
        this.$message.error(res.message)
      }
    })
  }
}
</script>

<style scoped lang="scss">
.welcome{
  width: 100%;
}
</style>

