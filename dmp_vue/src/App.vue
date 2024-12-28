<template>
  <div id="app">
    <Loading></Loading>
    <router-view/>
  </div>
</template>

<script>


import {defineComponent} from "vue";
import Loading from "@/components/loading/loading.vue";

export default defineComponent({
  components: {Loading}
})
</script>

<style scoped>



</style>
