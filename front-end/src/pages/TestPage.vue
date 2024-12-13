<template>
  <div>
    <PopupComponent
      :isOpen="isPopupOpen"
      :title="popupTitle"
      :type="popupType"
      :energyProduction="energyProduction"
      :energyConsumption="energyConsumption"
      :heatPumps="heatPumps"
      :electricCars="electricCars"
      :solarPanels.sync="solarPanels"
      :batteries.sync="batteries"
      @update:isOpen="isPopupOpen = $event"
      @update:solarPanels="solarPanels = $event"
      @update:batteries="batteries = $event"
    />
    <button @click="openPopup">Open Popup</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import PopupComponent from '../components/PopupComponent.vue';

export default defineComponent({
  components: {
    PopupComponent
  },
  setup() {
    const isPopupOpen = ref(false);
    const popupTitle = ref('Energie Overzicht Huis 2');
    const popupType = ref('huis');
    const energyProduction = ref(0.5);
    const energyConsumption = ref(1.2);
    const heatPumps = ref(2);
    const electricCars = ref(1);
    const solarPanels = ref(5);
    const batteries = ref(3);

    const openPopup = () => {
      isPopupOpen.value = true;
    };

    return {
      isPopupOpen,
      popupTitle,
      popupType,
      energyProduction,
      energyConsumption,
      heatPumps,
      electricCars,
      solarPanels,
      batteries,
      openPopup
    };
  }
});
</script>