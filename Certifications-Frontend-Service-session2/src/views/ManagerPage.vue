<template>

	<v-container class="grey lighten-5">
    <v-row no-gutters>
      <v-col cols="12" sm="6" md="8">
        <v-card class="pa-2" outlined tile>
          Click to add a certification
        </v-card>
      </v-col>
      <v-col cols="6" md="4">
        <AddCertificateDialog />
      </v-col>
    </v-row>

    <v-row no-gutters>
      <v-col cols="12" sm="6" md="8">
        <v-card class="pa-2" outlined tile>
          Click to add a certification
        </v-card>
      </v-col>
      <v-col cols="6" md="4">
        <DeleteCertificateDialog />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AddCertificateDialog from "../components/AddCertificateDialog.vue";
import DeleteCertificateDialog from "../components/DeleteCertificateDialog.vue";
export default {
  data() {
    return {
      showDeleteDialog: false,
      userEmail: ""
    };
  },
  components: {
    AddCertificateDialog,
    DeleteCertificateDialog
  },
  methods: {
    alert() {
      console.log("addmanager");
    }
  }
};
</script>