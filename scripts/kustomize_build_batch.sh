service=(
  'details'
  'productpage'
  'ratings'
  'reviews'
 )
images=(
  ${{ needs.build-and-push-image.outputs.details }}
  ${{ needs.build-and-push-image.outputs.productpage }}
  ${{ needs.build-and-push-image.outputs.ratings }}
  ${{ needs.build-and-push-image.outputs.reviews }}
)

for i in "${!service[@]}"; do
  s=${service[i]}
  image=${images[i]}

  for p in "${platform[@]}"; do
    for e in "${env[@]}"; do
      ./scripts/kustomize_build_ci.sh "$s" "$p" "$e" "$image"
    done
  done
done
