import { User } from "./user";

export interface UserContextType {
  user?: User;
  loading: boolean;
  getMe: () => Promise<User>;
}