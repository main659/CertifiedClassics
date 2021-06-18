<template>
  <div class="card-padding">
    <v-card width="300">
      <v-img
        class="white--text align-end"
        height="120px"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
      >
        <v-card-title>{{ certification.name }}</v-card-title>
      </v-img>
      <div style="height: 50px">
        <v-card-text class="align-end fill-height">
          <strong
            >{{ certification.price }} {{ certification.currency }}</strong
          >
        </v-card-text>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    certification: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style lang="scss" scoped>
.card-padding {
  padding: 30px;
}
</style>
