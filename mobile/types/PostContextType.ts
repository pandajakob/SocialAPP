import { Post } from "./post";

export interface PostContextType {
  loading: boolean;
  userPosts: Post[]
  
}