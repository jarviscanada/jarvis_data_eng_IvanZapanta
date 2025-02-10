<script setup>
import router from "@/router";
import { reactive } from "vue";
import { useToast } from "vue-toastification";
import axios from "axios";

const toast = useToast();

const form = reactive({
  firstName: "",
  lastName: "",
  email: "",
  dob: "",
  country: "",
});

const handleSubmit = async () => {
  const newTrader = {
    firstName: form.firstName,
    lastName: form.lastName,
    email: form.email,
    dob: form.dob,
    country: form.country,
  };
  try {
    const response = await axios.post(
      `http://localhost:5000/traders/`,
      newTrader
    );
    toast.success("Trader added successfully!");
    router.push("/");
  } catch (error) {
    console.error("Error adding trader", error);
    toast.error("Trader was not added.");
  }
};
</script>

<template>
  <section class="bg-blue-50 px-4 py-10 h-screen w-full pl-[100px]">
    <div class="container-xl lg:container m-auto">
      <section class="bg-blue-50">
        <div class="container m-auto max-w-2xl py-24">
          <div
            class="bg-white px-6 py-8 mb-4 shadow-md rounded-md border m-4 md:m-0"
          >
            <form @submit.prevent="handleSubmit">
              <h2 class="text-3xl text-center font-semibold mb-6">
                Add Trader
              </h2>

              <div class="mb-4">
                <label
                  for="firstName"
                  class="block text-gray-700 font-bold mb-2"
                  >First Name</label
                >
                <input
                  v-model="form.firstName"
                  type="text"
                  id="firstName"
                  name="firstName"
                  class="border rounded w-full py-2 px-3 mb-2"
                  placeholder="Enter first name"
                  required
                />
              </div>

              <div class="mb-4">
                <label for="lastName" class="block text-gray-700 font-bold mb-2"
                  >Last Name</label
                >
                <input
                  v-model="form.lastName"
                  type="text"
                  id="lastName"
                  name="lastName"
                  class="border rounded w-full py-2 px-3 mb-2"
                  placeholder="Enter last name"
                  required
                />
              </div>

              <div class="mb-4">
                <label for="dob" class="block text-gray-700 font-bold mb-2"
                  >Date of Birth</label
                >
                <input
                  v-model="form.dob"
                  type="date"
                  id="dob"
                  name="dob"
                  class="border rounded w-full py-2 px-3 mb-2"
                  required
                />
              </div>

              <div class="mb-4">
                <label for="country" class="block text-gray-700 font-bold mb-2"
                  >Country</label
                >
                <input
                  v-model="form.country"
                  type="text"
                  id="country"
                  name="country"
                  class="border rounded w-full py-2 px-3 mb-2"
                  placeholder="Enter country"
                  required
                />
              </div>

              <div class="mb-4">
                <label for="email" class="block text-gray-700 font-bold mb-2"
                  >Email Address</label
                >
                <input
                  v-model="form.email"
                  type="email"
                  id="email"
                  name="email"
                  class="border rounded w-full py-2 px-3 mb-2"
                  placeholder="Enter email"
                  required
                />
              </div>

              <div>
                <button
                  class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                  type="submit"
                >
                  Add Trader
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </div>
  </section>
</template>
