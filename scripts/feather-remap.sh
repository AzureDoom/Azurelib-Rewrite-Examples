#!/usr/bin/env bash
# Applies scripts/feather-names.tsv to src/: every row whose two names differ is renamed, both as a fully qualified
# name (imports, mixin descriptors) and as a simple class name. Then updates column 1 so the table stays current.
set -euo pipefail
cd "$(dirname "$0")/.."
table=scripts/feather-names.tsv
tmp=$(mktemp)

while IFS=$'\t' read -r from to rest; do
    [[ -z "${from}" || "${from}" == \#* ]] && continue
    [[ "${from}" == "${to}" ]] && continue
    from_simple=${from##*.}
    to_simple=${to##*.}
    from_slash=${from//./\/}
    to_slash=${to//./\/}
    echo "rename ${from} -> ${to}"
    grep -rlE "${from_simple}|${from_slash}" src/main | while read -r f; do
        sed -i \
            -e "s#${from//./\\.}\b#${to}#g" \
            -e "s#${from_slash}\b#${to_slash}#g" \
            -e "s#\b${from_simple}\b#${to_simple}#g" \
            "$f"
    done
done < "${table}"

# Column 1 now matches column 2.
awk -F'\t' 'BEGIN{OFS="\t"} /^#/ || NF<2 {print; next} {$1=$2; print}' "${table}" > "${tmp}"
mv "${tmp}" "${table}"
