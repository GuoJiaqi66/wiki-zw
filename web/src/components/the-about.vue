<template>
  
  <a-row>
    <a-col :span="12" style="text-align: center">
      <h1>关于网站</h1>
      <h2>技术支持:</h2> <br />
      <a-row>
        <a-col :span="11">
          前端: <br />
          前端框架:<a href="https://cli.vuejs.org/zh/">Vue CLI3</a> <br />
          Vue全家桶: <a href="https://vuex.vuejs.org/zh/guide/">Vuex</a>
          <a href="https://router.vuejs.org/zh/">vue-root</a> <br />
          网络请求: <a href="http://www.axios-js.com/">axios</a> <br />
          ui框架: <a href="https://next.antdv.com">Ant Design Vue</a> <br />
          富文本框架: <a href="https://www.wangeditor.com/">wangEditor</a> <br />
          可视化图标库: <a href="https://echarts.apache.org/zh/index.html">ECharts</a> <br />
          脚本语言: <a href="https://www.typescriptlang.org/">TypeScript</a> <br />
        </a-col>
        <a-divider type="vertical" style="height: 180px; background-color: #7cb305" />
        <a-col :span="11">
          后端: <br />
          后端框架: <a href="https://spring.io/projects/spring-boot">Spring Boot</a>  <br />
          数据库: <a href="https://www.mysql.com/">MySQL</a>  <br />
          代码生成器: <a href="http://mybatis.org/generator/">MyBatis</a>  <br />
          数据库: <a href="https://redis.io/">Redis</a>  <br />
          消息通知: <a href="https://socket.io/">WebSocket</a>  <br />
        </a-col>
        <a-divider style="height: 2px; background-color: #7cb305" />
      </a-row>
      <a-card title="代码管理" :bordered="false" style="width: 100%">
        <p>
          本项目已上传至<a href="https://github.com/">GitHub</a>，想看看源码的兄弟姐妹可以去clone，<br />
          如果你有什么什么想要了解的，可以联系我。
          非常乐意被打扰。<br />
          同时，此项目还存在大量细节问题，
          如果你乐意修改，我也会非常感谢。<br />
          同时此项目为 <a href="https://git-scm.com/">Git</a>管理项目
        </p>
        <div>
          <img style="width: 30%" src="GitHub.png" alt="">
          <br>
          <img style="width: 30%" src="zw.png" alt="">
        </div>
      </a-card>
      <hr>
      <h2>感谢：</h2>
      <h4>代码编辑工具：<a href="https://www.jetbrains.com/idea/">JetBrains IntelliJ IDEA 2020</a> </h4>
    </a-col>
    <a-col :span="12" style="text-align: center">
      <h1>关于作者</h1>
      <h2>欢迎打扰:</h2> <br />
      <h2>联系作者，拿到本站的管理权:</h2>
      qq: 858064356 <br />
      邮箱: 858064356@qq.com  &nbsp;&nbsp; Ja7.guo@outlook.com <br />
      微信: Si-Fa-Un-and-Fu <br />
      <hr>
      <p>
        本人先为一名在校生，如果你对前端，后端，有兴趣，<br />
        或者对此有些技能，想要练习或者学习，或者有自己的一些想法，<br />
        我觉得也许我们会成为伙伴，不只是现在的伙伴
      </p>
      <div>
        <img style="width: 30%" src="zfb.jpg" alt="">
        <img style="width: 30%" src="wxzsm.jpg" alt="">
        <img style="width: 30%" src="wxzf.jpg" alt="">
      </div>
      <br>
      <h2>感谢的人:</h2>
      <p>
        太过矫情，不好开口，如果你愿意听我讲述，欢迎打扰 <br>
        感谢所有人， 谢谢
      </p>
    </a-col>
  </a-row>
</template>

<script>
    export default {
        name: "the-about"
    }
</script>

<style scoped>

</style>