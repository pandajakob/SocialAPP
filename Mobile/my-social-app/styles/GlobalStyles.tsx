import { StyleSheet } from "react-native";


export const containers = StyleSheet.create({
  standard: {
    flex: 1,
    justifyContent: "center",
    padding: 20,
  },
  left: {
    flex: 1,
    justifyContent: "center",
    padding: 20,
  },

  rounded: {
    // Layout
    alignItems: "center",
    justifyContent: "center",
    padding: 20,

    // Border & Shape
    borderRadius: 16, // Slightly more rounded for a modern feel
    backgroundColor: "#ffffff",
    borderWidth: 1,
    borderColor: "#f0f0f0", // Subtle border to define the shape

    // iOS Shadow
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 8,

    // Android Shadow
    elevation: 5,
  },
});

export const text = StyleSheet.create({
  heading: {
    paddingVertical: 25,
    fontWeight: "bold",
    fontSize: 30,
  },
});
