package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

@Name("Piston Block")
@Description({"Returns piston block in pistons events."})
@Examples({
		"on block piston push:",
		"\tbroadcast \"piston at location: %event-piston-block% pushed block %event-block% at %event-location%"
})
@Since("1.0")

public class ExprBlockMovePistonBlock extends SimpleExpression<Block> {

	static {
		RegisterManager.registerExpression(
			ExprBlockMovePistonBlock.class,
			Block.class,
			ExpressionType.SIMPLE,
			"[morkazsk event(-| )piston(-| )block"
		);
	}

	@Override
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		Class<? extends Event>[] eventClasses = new Class[] {BlockPistonRetractEvent.class, BlockPistonExtendEvent.class};
		if (!ScriptLoader.isCurrentEvent(eventClasses)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClasses.toString()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event event, boolean debug) {
		return "event-piston-block";
	}

	@Override
	@javax.annotation.Nullable
	protected Block[] get(Event event) {
		if (event instanceof BlockPistonExtendEvent){
			return new Block[] {((BlockPistonExtendEvent)event).getBlock()};
		}
		return new Block[] {((BlockPistonRetractEvent)event).getBlock()};
	}
}
