import { ThemedText } from "@/components/themed-text";
import { ThemedView } from "@/components/themed-view";
import { containers } from "@/styles/GlobalStyles";

export default function Chats() {
  return (
    <ThemedView style={containers.standard}>
      <ThemedText> Chats</ThemedText>
    </ThemedView>
  );
}
