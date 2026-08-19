import { Post } from "./post";

export interface PostContextType {
  loading: boolean;
  userPosts: Post[]
  feed: Post[]  
  createPost: (post: Omit<Post, "postId" | "date">) => Promise<Post>
}