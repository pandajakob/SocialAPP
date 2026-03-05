import { ThemedText } from "@/components/themed-text";
import { ThemedView } from "@/components/themed-view";
import { containers, text } from "@/styles/GlobalStyles";

export default function Profile() {
  const mockProfile = {
    firstname: "John",
    lastname: "Doe",
    phone: "12 35 81 32",
    email: "John@doe.dk",
  };

  return (
    <ThemedView style={containers.left}>
      <ThemedText style={text.heading}> Profile </ThemedText>
      <ThemedView style={containers.rounded}>
        <ThemedText> {mockProfile.firstname} </ThemedText>
      </ThemedView>
    </ThemedView>
  );
}
