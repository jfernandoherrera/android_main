package com.amtechventures.tucita.model.context.blockade;


import com.amtechventures.tucita.model.domain.blockade.Blockade;
import com.amtechventures.tucita.model.domain.venue.Venue;

public class BlockadeContext {

    public static BlockadeContext context(BlockadeContext blockadeContext) {

        if (blockadeContext == null) {

            blockadeContext = new BlockadeContext();

        }

        return blockadeContext;

    }

    public BlockadeContext() {

        blockadeRemote = new BlockadeRemote();

    }

    private BlockadeRemote blockadeRemote;

    public void getBlockadeFromVenue(Venue venue, BlockadeCompletion.ErrorCompletion completion){

      blockadeRemote.getBlockadeFromVenue(venue, completion);

    }

    public void saveBlockade(Blockade blockade, BlockadeCompletion.ErrorCompletion completion) {

        blockadeRemote.saveBlockade(blockade, completion);
    }
}
