import React, { useEffect, useRef } from "react";
import Animated, {
  FadeInDown,
  FadeOut,
  LinearTransition,
} from "react-native-reanimated";
import PostCard from "./PostCard";
import { Post } from "@/types/post";

interface AnimatedPostCardProps {
  post: Post;
  /**
   * Ids of posts that have already been shown. A card only plays its entering
   * animation the first time its post appears, so scrolling in a virtualized
   * list (which unmounts and remounts cells) does not re-trigger it.
   */
  seenIds: Set<string>;
}

export default function AnimatedPostCard({
  post,
  seenIds,
}: AnimatedPostCardProps) {
  const isNew = useRef(!seenIds.has(post.id)).current;

  useEffect(() => {
    seenIds.add(post.id);
  }, [post.id, seenIds]);

  return (
    <Animated.View
      entering={isNew ? FadeInDown.duration(250) : undefined}
      exiting={FadeOut.duration(150)}
      layout={LinearTransition.duration(250)}
    >
      <PostCard post={post} />
    </Animated.View>
  );
}
