<script setup>
import { ref } from "vue";
import { onMounted } from "vue";
import axios from "axios";

const quotes = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:5000/quotes`);
    console.log(response.data);
    quotes.value = response.data;
  } catch (error) {
    console.error("Error fetching data", error);
  }
});
</script>

<template>
  <section class="bg-blue-50 px-4 py-10 min-h-screen w-full pl-[100px]">
    <div class="container-xl lg:container m-auto">
      <div class="flex justify-between items-center mb-6">
        <h2 class="text-3xl font-bold text-red-500">Stock Quotes</h2>
      </div>
      <div
        class="overflow-x-auto bg-white rounded-lg shadow-md max-h-[400px] sm:max-h-[550] md:max-h-[600px]"
      >
        <table class="min-w-full table-auto">
          <thead>
            <tr class="bg-gray-100 text-left">
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Symbol
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Last Price
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Bid Price
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Bid Size
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Ask Price
              </th>
              <th class="px-6 py-3 text-sm font-semibold text-gray-600">
                Ask Size
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="quote in quotes" :key="quotes.ticker">
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ quote.ticker }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                ${{ quote.lastPrice }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                ${{ quote.bidPrice }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ quote.bidSize }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                ${{ quote.askPrice }}
              </td>
              <td class="px-6 py-4 text-sm text-gray-700 font-medium">
                {{ quote.askSize }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>
