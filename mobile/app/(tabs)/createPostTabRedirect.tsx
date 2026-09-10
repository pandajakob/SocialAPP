// app/(tabs)/create.tsx
import { useFocusEffect, useRouter } from "expo-router";
import { useCallback } from "react";

export default function CreateTabRedirect() {
  const router = useRouter();

  useFocusEffect(
    useCallback(() => {
      router.push("/createPost");

    }, [router])
  );

  return null;
}