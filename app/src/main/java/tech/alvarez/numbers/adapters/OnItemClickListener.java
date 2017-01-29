package tech.alvarez.numbers.adapters;

import tech.alvarez.numbers.models.youtube.search.ItemSearchResponse;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public interface OnItemClickListener {


    void onItemClick(ItemSearchResponse channel);
}
