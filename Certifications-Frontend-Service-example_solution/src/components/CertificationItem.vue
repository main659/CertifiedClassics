<template>
  <div class="card-padding">
    <v-card :width="width">
      <v-img
        class="white--text align-end"
        height="120px"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
      >
        <v-card-title>{{ certification.name }}</v-card-title>
      </v-img>
      <div style="word-wrap: break-word; height: 50px;">
        <v-card-text class="align-end fill-height">
          <strong
            >{{ certification.price }} {{ certification.currency }}</strong
          >
        </v-card-text>
      </div>
      <v-card-text>{{ certification.status }}</v-card-text>
    </v-card>
  </div>
</template>

<script>
import Vue from "vue";
export default Vue.extend({
  name: "CertificationItem",
  props: {
    certification: {
      type: Object,
      required: true
    },
    width: {
      type: Number,
      required: true
    }
  }
});
</script>

<style scoped>
.hidden {
  color: red;
}
.card-padding {
  padding: 30px !important;
}

.card-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.card-button-text {
  display: inherit;
}

.vertical-center {
  min-height: 100%; /* Fallback for browsers do NOT support vh unit */
  min-height: 100vh; /* These two lines are counted as one :-)       */

  display: flex;
  align-items: center;
}
.icons {
  width: 110px;
  margin: 15px;
}

h1 {
  text-align: center;
}

.action_txt {
  color: black;
  text-align: center;
  font-size: 20px;
}
</style>
