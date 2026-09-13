export interface Category {
  id: number;
  name: string;
  key: string;
  emoji: string;
  parentCategoryId: number | null
}