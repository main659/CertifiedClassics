<template>
  <div>
    <v-card width="300" style="margin-left:30px" elevation="7">
      <v-card-title>{{ voucher.voucherCode }}</v-card-title>
      <v-card-subtitle>Valid until: {{ voucher.validUntil }}</v-card-subtitle>
      <div style="height: 50px">
        <v-card-text class="align-end fill-height">
          State:{{ voucher.state }}
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
