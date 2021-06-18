<template>
  <div>{{ currentCertification.url }}</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "CertificationDetail",
  data() {
    return {
      currentCertification: {},
    };
  },
  created() {
    console.log(this.certifications);
    this.currentCertification = this.certifications.find(
      ({ id }) => id == this.$route.params.id
    );
  },
  computed: {
    ...mapGetters(["certifications"]),
  },
};
</script>
