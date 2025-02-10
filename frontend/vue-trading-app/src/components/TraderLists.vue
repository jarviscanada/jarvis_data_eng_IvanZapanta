<script setup>
import { ref, onMounted } from "vue";
import { useRoute, RouterLink } from "vue-router";
import { useToast } from "vue-toastification";
import router from "@/router";
import axios from "axios";

// const route = useRoute();

const toast = useToast();
const traders = ref([]);
// const traderId = route.params.id;

const deleteTrader = async (traderId) => {
  try {
    // console.log("Trader ID:", traderId);
    const confirm = window.confirm(
      `Are you sure you wanted to delete this trader?`
    );
    if (confirm) {
      await axios.delete(`http://localhost:5000/traders/${traderId}`);
      toast.success("Trader deleted successfully!");

      // Remove the deleted trader from the local list immediately
      traders.value = traders.value.filter((trader) => trader.id !== traderId);
    }
  } catch (error) {
    toast.error("Trader was not deleted.");
    console.error("Error deleting trader", error);
  }
};

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:5000/traders`);
    console.log(response.data);
    traders.value = response.data;
  } catch (error) {
    console.error("Error fetching data", error);
  }
});
</script>

<template>
  <section class="bg-blue-50 px-4 py-10 min-h-screen w-full pl-[100px]">
    <div class="container-xl lg:container m-auto">
      <div class="flex justify-between items-center mb-6">
        <h2 class="text-3xl font-bold text-red-500">Traders</h2>
        <RouterLink
          to="/traders/add"
          class="inline-block bg-green-500 text-white rounded-lg px-4 py-2 hover:bg-green-600"
        >
          Add Trader
        </RouterLink>
      </div>
      <div
        class="overflow-x-auto bg-white rounded-lg shadow-md max-h-[400px] sm:max-h-[550] md:max-h-[600px]"
      >
        <table class="min-w-full table-auto">
          <thead>
            <tr class="bg-gray-100 text-left">
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                First Name
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Last Name
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Email
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Date of Birth
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Country
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Actions
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="trader in traders" :key="trader.id">
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ trader.firstName }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ trader.lastName }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ trader.email }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ trader.dob }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ trader.country }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                <RouterLink
                  :to="`/traders/edit/${trader.id}`"
                  class="pi pi-pencil text-red-500 hover:text-white hover:bg-red-500 py-2 px-2 rounded-full"
                ></RouterLink>
                |
                <button
                  @click="deleteTrader(trader.id)"
                  class="pi pi-trash text-red-500 hover:text-white hover:bg-red-500 py-2 px-2 rounded-full"
                ></button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>
