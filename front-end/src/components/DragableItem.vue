<template>
  <div
    class="draggable-item"
    draggable="true"
    @dragstart="handleDragStart"
    @dragend="handleDragEnd"
  >
    <img :src="imageSrc" :alt="label" class="item-image" />
    <div class="item-label">{{ label }}</div>
    <div class="item-cost">
      <img class="coin" src="/coin.png" alt="Coin" />
      {{ cost }}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "DraggableItem",
  props: {
    itemType: {
      type: String,
      required: true,
    },
    imageSrc: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    cost: {
      type: Number,
      required: true,
    },
  },
  emits: ["drag-item"],
  setup(props, { emit }) {
    const handleDragStart = (event: any) => {
      if (event.dataTransfer) {
        event.dataTransfer.setData("text/plain", props.itemType);
        event.dataTransfer.effectAllowed = "move";
        event.target.classList.add("dragging");
        emit("drag-item", props.itemType);
      }
    };

    const handleDragEnd = (event: any) => {
      event.target.classList.remove("dragging");
    };

    return {
      handleDragStart,
      handleDragEnd,
    };
  },
});
</script>

<style scoped>
.draggable-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  background-color: rgba(0, 0, 0, 0.1);
  border: 1px solid #4a4a4a;
  border-radius: 5px;
  cursor: move;
  width: 80%;
  transition: transform 0.2s, background-color 0.2s;
}

.coin {
  width: 16px;
  margin-top: -3px;
}

.draggable-item:hover {
  background-color: rgba(123, 123, 123, 0.2);
  transform: scale(1.05);
}

.draggable-item.dragging {
  opacity: 0.5;
}

.item-image {
  height: 80px;
  margin-bottom: 10px;
}

.item-label {
  color: #fff;
  font-size: 12px;
  letter-spacing: normal;
  font-weight: bold;
  font-family: "Press Start 2P";
}

.item-cost {
  color: #ccc;
  font-size: 9px;
  font-family: "Press Start 2P";
  display: flex;
  align-items: center;
  gap: 5px;
}
</style>