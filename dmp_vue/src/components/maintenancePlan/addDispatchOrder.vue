<template>
  <el-dialog v-model="dialogTableVisible" title="Shipping address" width="800">
    <el-table :data="gridData">
      <el-table-column property="date" label="Date" width="150" />
      <el-table-column property="name" label="Name" width="200" />
      <el-table-column property="address" label="Address" />
    </el-table>
  </el-dialog>
</template>

<script setup>

</script>



<style scoped>

</style>