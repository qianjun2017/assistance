<template>
  <section>
    <div class="unauth">
      <span>对不起，您无权访问[<el-button type="text" @click="back">返回</el-button>]</span>
    </div>
  </section>
</template>

<script>
export default {
  methods: {
    back: function(){
      this.$router.go(-1)
    }
  }
}
</script>


<style lang="scss" scoped>
.unauth{
  padding-top: 300px;
  text-align: center;
}
</style>
