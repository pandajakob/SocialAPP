import { Category } from "@/types/category";

export type CategoryContextType = {
  categories: Category[];
  mainCategories: Category[];
  loading: boolean;
  getCategoryById: (id: number) => Promise<Category>;
  getCategoryByName: (name: string) => Promise<Category>;
  getSubCategories: (name: string) => Promise<Category[]>;
};