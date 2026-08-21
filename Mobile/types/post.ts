import { Category } from "./category";
import { Location } from "./location";

export interface Post {
  postId: string;
  title: string;
  description: string;
  location: Location;
  date: string;
  ageFrom: number;
  ageTo: number;
  categories: Category[];
  photoUrl: string;
}