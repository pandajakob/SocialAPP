import { useEffect, useMemo, useRef, useState } from "react";
import { useCategory } from "@/context/CategoryContext";
import { Post } from "@/types/post";

/**
 * Filters posts by the categories selected in the category filter.
 *
 * A post matches when at least one of its categories has an ultimate parent
 * that is among the selected (main) categories. Ultimate parents are resolved
 * with the backend endpoint and cached per category id, so each distinct
 * category is only looked up once. Categories without a parent are their own
 * ultimate parent and need no request.
 */
export function useFilteredFeed(posts: Post[]) {
  const { selectedCategoryIds, getUltimateParentCategory } = useCategory();

  // categoryId -> ultimate parent id (null if the lookup failed)
  const [ultimateParents, setUltimateParents] = useState<
    Record<number, number | null>
  >({});

  const fetchParent = useRef(getUltimateParentCategory);
  fetchParent.current = getUltimateParentCategory;
  const requested = useRef(new Set<number>());

  const isFiltering = selectedCategoryIds.length > 0;

  // Subcategory ids in the feed whose ultimate parent is not known yet
  const unresolvedIds = useMemo(() => {
    if (!isFiltering) return [];
    const ids = new Set<number>();
    for (const post of posts) {
      for (const category of post.categories) {
        if (
          category.parentCategoryId !== null &&
          ultimateParents[category.id] === undefined
        ) {
          ids.add(category.id);
        }
      }
    }
    return [...ids];
  }, [posts, isFiltering, ultimateParents]);

  useEffect(() => {
    const toFetch = unresolvedIds.filter((id) => !requested.current.has(id));
    if (toFetch.length === 0) return;
    toFetch.forEach((id) => requested.current.add(id));

    Promise.all(
      toFetch.map(async (id): Promise<[number, number | null]> => {
        try {
          const parent = await fetchParent.current(id);
          return [id, parent.id];
        } catch (error) {
          console.log("Error resolving ultimate parent:", error);
          return [id, null];
        }
      })
    ).then((results) => {
      setUltimateParents((current) => ({
        ...current,
        ...Object.fromEntries(results),
      }));
    });
  }, [unresolvedIds]);

  const filteredPosts = useMemo(() => {
    if (!isFiltering) return posts;

    return posts.filter((post) =>
      post.categories.some((category) => {
        const ultimateParentId =
          category.parentCategoryId === null
            ? category.id
            : ultimateParents[category.id];
        return (
          ultimateParentId != null &&
          selectedCategoryIds.includes(ultimateParentId)
        );
      })
    );
  }, [posts, isFiltering, selectedCategoryIds, ultimateParents]);

  return { filteredPosts, resolving: unresolvedIds.length > 0 };
}
