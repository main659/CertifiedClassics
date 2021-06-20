<template>
  <v-container class="grey lighten-5">
    <v-row no-gutters>
      <v-col cols="12" sm="6" md="8">
        <v-card class="pa-2" outlined tile>
          Click to delete a user via providing users email !!PERMANENT!!
        </v-card>
      </v-col>
      <v-col cols="6" md="4">
        <DeleteDialog />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import DeleteDialog from "../components/DeleteDialog.vue";
export default {
  data() {
    return {
      showDeleteDialog: false
    };
  },
  components: {
    DeleteDialog
  },
  methods: {
    alert() {
      console.log("addmanager");
    }
  }
};
</script>
