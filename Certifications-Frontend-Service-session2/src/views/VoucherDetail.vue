<template>
  <div>{{ currentVoucher.voucherCode }}</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "VoucherDetail",
  data() {
    return {
      currentVoucher: {},
    };
  },
  created() {
    console.log(this.vouchers);
    this.currentVoucher = this.vouchers.find(
      ({ id }) => id == this.$route.params.id
    );
  },
  computed: {
    ...mapGetters(["vouchers"]),
  },
};
</script>
