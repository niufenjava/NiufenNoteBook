<template>
  <div>
    <input type="button" value="减少" @click="remove">
    <input type="button" value="增加" @click="add">
    <br>
    <!-- 如果在组件中想要访问 store 中的数据，只能通过 this.$store.state.cont 访问 -->
    <input type="text" v-model="$store.state.count">
  </div>
</template>

<script>
export default {
  data() {
    return {
      // count: 0
    };
  },
  methods: {
    add() {
      // 千万不要这么用，不符合 vuex 的设计理念
      // this.$store.state.count++;
      this.$store.commit("increment");
    },
    remove() {
      this.$store.commit("subtract", { c: 1, d: 0 });
    }
  },
  computed:{
    fullname: {
      get(){},
      set(){}
    }
  }
};
</script>

<style lang="scss" scoped>

</style>
