<template>
  <div id="nav">
    <a-layout id="components-layout-demo-top-side-2">
          <TheHeader />

          <router-view/>

          <TheFooter />
    </a-layout>

  </div>
</template>

<script lang="ts">
  import TheHeader from './components/the-header.vue'
  import TheFooter from './components/the-footer.vue'

  export default {
    name: "App",
    components:  {
      TheHeader,
      TheFooter
    }
  }
</script>

<!--
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
-->
