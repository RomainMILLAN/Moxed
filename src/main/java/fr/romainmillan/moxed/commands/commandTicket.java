package fr.romainmillan.moxed.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.romainmillan.moxed.Main;
import fr.romainmillan.moxed.manager.ItemManager;
import fr.romainmillan.moxed.messages.ModerationMessages;
import fr.romainmillan.moxed.messages.MoxedMessage;
import fr.romainmillan.moxed.object.Ticket;
import fr.romainmillan.moxed.service.RankerService;

public class commandTicket implements CommandExecutor {
    private Main main;
    public commandTicket(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("ticket")){
            if(!(sender instanceof Player)){
                sender.sendMessage(MoxedMessage.EM_NOT_PLAYER.getMessage());
                return false;
            }

            Player p = (Player) sender;
            String commandAtUse = "/ticket [ticket-message]";

            if(args.length >= 1 && !args[0].equalsIgnoreCase("see")){
                StringBuilder ticketMessage = new StringBuilder();

                for(String str : args){
                    ticketMessage.append(str);
                    ticketMessage.append(" ");
                }

                Ticket newTicket = new Ticket(getTicketIdMax(), p.getName(), ticketMessage.toString());
                Main.tickets.add(newTicket);

                p.sendMessage(ModerationMessages.TICKET_CREATE_SUCESS.getMessages());
                return true;

            }else {
                if(RankerService.isInStaff(p)){
                    if(args.length == 0) {
                        
                        int scale = getScaleInventoryTicket(Main.tickets.size());
                        Inventory ticketsInventory = Bukkit.createInventory(null, scale, "Tickets");

                        int i = 0;
                        for(Ticket ticket : Main.tickets){
                            ticketsInventory.setItem(i, (ItemStack) ItemManager.craftItem(Material.ANVIL, ticket.getId() + " > " + ticket.getDescription()));
                            i++;
                        }

                        while(i<scale){
                            ticketsInventory.setItem(i, (ItemStack) ItemManager.craftItemNone());
                            i++;
                        }

                        p.openInventory(ticketsInventory);
                        return true;

                    }else {
                        p.sendMessage(MoxedMessage.EM_ERRORCMD_WITH_COMMAND_TO_USE.getMessage() + commandAtUse);
                        return false;
                    }
                }else {
                    p.sendMessage(MoxedMessage.EM_ERRORPERM.getMessage());
                    return false;
                }
            }
        }

        return false;

    }

    private static int getScaleInventoryTicket(int nbTicket) {
        if (nbTicket <= 9) {
            return 1 * 9;
        } else if (nbTicket <= 18) {
            return 2 * 9;
        } else if (nbTicket <= 27) {
            return 3 * 9;
        } else if (nbTicket <= 36) {
            return 4 * 9;
        } else if (nbTicket <= 45) {
            return 5 * 9;
        } else {
            return 6 * 9;
        }
    }

    private static int getTicketIdMax(){
        int res = 0;
        for(Ticket ticket : Main.tickets){
            if(res < ticket.getId()){
                res = ticket.getId();
            }
        }

        return res;
    }

}
