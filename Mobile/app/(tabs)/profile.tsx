import { Text, View } from "react-native";
import "../../global.css";

export default function Profile() {
  const mockProfile = {
    firstname: "John",
    lastname: "Doe",
    phone: "12 35 81 32",
    email: "John@doe.dk",
  };

  return (
    <View className="flex-1 bg-gray-100 px-6 pt-24">
      <Text className="text-3xl font-bold text-gray-800 mb-6">Profile</Text>

      <View className="bg-white rounded-2xl p-6 shadow-md">
        <View className="mb-4">
          <Text className="text-gray-500 text-sm">Name</Text>
          <Text className="text-lg font-semibold text-gray-800">
            {mockProfile.firstname} {mockProfile.lastname}
          </Text>
        </View>

        <View className="mb-4">
          <Text className="text-gray-500 text-sm">Email</Text>
          <Text className="text-lg text-gray-800">{mockProfile.email}</Text>
        </View>

        <View>
          <Text className="text-gray-500 text-sm">Phone</Text>
          <Text className="text-lg text-gray-800">{mockProfile.phone}</Text>
        </View>
      </View>
    </View>
  );
}
