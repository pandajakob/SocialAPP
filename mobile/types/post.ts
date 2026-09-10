import { Category } from "./category";
import { Location } from "./location";
import { User } from "./user";

export interface Post {
  id: string;
  title: string;
  description: string;
  location: Location;
  date: string;
  ageFrom: number;
  ageTo: number;
  user: User
  categories: Category[];
  photoUrl: string;
}