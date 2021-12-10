<template>
  <a-layout>
  <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
    <TheAbout />
  </a-layout-content>
  </a-layout>
  
</template>

<script lang="ts">
  import TheAbout from '@/components/the-about.vue'
  import {onMounted} from 'vue'
  
  
    export default {
        name: "About",
        components: {
            TheAbout
        },
        setup() {
            onMounted(() => {
                alert("这不是我的编程风格，我的风格比这要好很多(源码,源码，源码)")
            })
        }
    }
</script>
