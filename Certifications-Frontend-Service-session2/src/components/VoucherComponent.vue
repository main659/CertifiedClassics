<template>
  <div>
    <v-card width="300" elevation="7">
      <v-card-title>{{ voucher.voucherCode }}</v-card-title>
      <v-card-subtitle>{{ voucher.validUntil }}</v-card-subtitle>
      <div style="height: 50px">
        <v-card-text class="align-end fill-height">
          {{ voucher.state }}
        </v-card-text>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    voucher: {
      type: Object,
      required: true
    }
  }
};
</script>
