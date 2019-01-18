package com.morkaz.morkazsk.expressions.dedicated;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

@Name("Fishing Caught Entity")
@Description({
		"Caught entity (hooked by fishing rod) in fishing event. It may be also fish drop item in specific fishing state."
})
@Examples({
		"on fishing:",
		"\tif fishing state is \"CAUGHT_FISH\":",
		"\t\tsend \"Wow, you caught %items within caught entity%!\""
})
@Since("1.0")

public class ExprFishingCaughtEntity extends SimpleExpression<Entity> {

	static {
		RegisterManager.registerExpression(
				ExprFishingCaughtEntity.class,
				Entity.class,
				ExpressionType.SIMPLE,
				"[fishing(-| )]caught(-| )entity"
		);
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		Class<? extends Event> eventClass = PlayerFishEvent.class;
		if (!ScriptLoader.isCurrentEvent(eventClass)) {
			Skript.error("[MorkazSk] This expression can be used only in: \""+eventClass.getName()+"\"!");
			return false;
		}
		return true;
	}

	@Override
	public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
		return "fishing caught entity";
	}

	@Override
	@javax.annotation.Nullable
	protected Entity[] get(Event event) {
		return new Entity[] {((PlayerFishEvent)event).getCaught()};
	}

}
