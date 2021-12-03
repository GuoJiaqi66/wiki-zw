<template>
  <div id="nav">
      <a-layout-header class="header">
          <TheHeader />

          <router-view/>

          <TheFooter />
      </a-layout-header>

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
