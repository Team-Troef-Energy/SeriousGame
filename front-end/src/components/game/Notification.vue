<template>
  <v-dialog v-model="status" transition="dialog-bottom-transition" width="auto">
    <template v-slot:default="">
      <v-card>
        <v-card-text class="text-h2 pa-12">
          {{ message }}
        </v-card-text>
        <v-card-actions class="justify-end">
          <NavigateButton :to="navigateBackUrl" label="Ga terug" />
        </v-card-actions>
      </v-card>
      <div v-if="showConfetti" class="container">
        <div class="confetti" v-for="n in 10" :key="n"></div>
      </div>
    </template>
  </v-dialog>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import NavigateButton from "./NavigateButton.vue";

export default defineComponent({
  name: "Notification",
  components: {
    NavigateButton,
  },
  props: {
    status: {
      type: Boolean,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
    showConfetti: {
      type: Boolean,
      default: false,
    },
    navigateBackUrl: {
      type: String,
      required: true,
    },
  },
});
</script>

<style scoped>
.v-overlay--active {
  background: rgb(0 0 0 / 0.5);
}

.container {
  width: 100%;
  height: 100%;
}

.card {
  padding: 2em;
}

.confetti {
  width: 15px;
  height: 15px;
  background-color: #f2d74e;
  position: absolute;
  top: 0;
  left: 50%;
  animation: confetti 5s ease-in-out -2s infinite;
  transform-origin: left top;
}

.confetti:nth-child(1) {
  background-color: #f2d74e;
  left: 10%;
  animation-delay: 0;
}
.confetti:nth-child(2) {
  background-color: #95c3de;
  left: 20%;
  animation-delay: -5s;
}
.confetti:nth-child(3) {
  background-color: #ff9a91;
  left: 30%;
  animation-delay: -3s;
}
.confetti:nth-child(4) {
  background-color: #f2d74e;
  left: 40%;
  animation-delay: -2.5s;
}
.confetti:nth-child(5) {
  background-color: #95c3de;
  left: 50%;
  animation-delay: -4s;
}
.confetti:nth-child(6) {
  background-color: #ff9a91;
  left: 60%;
  animation-delay: -6s;
}
.confetti:nth-child(7) {
  background-color: #f2d74e;
  left: 70%;
  animation-delay: -1.5s;
}
.confetti:nth-child(8) {
  background-color: #95c3de;
  left: 80%;
  animation-delay: -2s;
}
.confetti:nth-child(9) {
  background-color: #ff9a91;
  left: 90%;
  animation-delay: -3.5s;
}
.confetti:nth-child(10) {
  background-color: #f2d74e;
  left: 100%;
  animation-delay: -2.5s;
}

@keyframes confetti {
  0% {
    transform: rotateZ(15deg) rotateY(0deg) translate(0, 0);
  }
  25% {
    transform: rotateZ(5deg) rotateY(360deg) translate(-5vw, 20vh);
  }
  50% {
    transform: rotateZ(15deg) rotateY(720deg) translate(5vw, 60vh);
  }
  75% {
    transform: rotateZ(5deg) rotateY(1080deg) translate(-10vw, 80vh);
  }
  100% {
    transform: rotateZ(15deg) rotateY(1440deg) translate(10vw, 110vh);
  }
}
</style>
