import { Category } from "./category";
import { Post } from "./post";

export interface User {
  email: string;
  firstName: string;
  id: string;
  interests: Category[];
  age: number;
  lastName: string;
  phoneNumber: string;
  photoUrl: string;
}
