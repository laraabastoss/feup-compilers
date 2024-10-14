package pt.up.fe.comp2024.optimization;

import org.antlr.v4.runtime.misc.Pair;
import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.ollir.OllirResult;

import java.awt.*;
import java.util.*;

public class RegisterAllocationVisitor {
    OllirResult ollirResult;
    Integer askedRegister;
    Set<Element> operands = new HashSet<>();
    Map<Instruction, Set<Element>> in = new HashMap<>();
    Map<Instruction, Set<Element>> out = new HashMap<>();
    Map<Instruction, Set<Element>> used = new HashMap<>();
    Map<Instruction, Set<Element>> defined = new HashMap<>();
    Map<Element, Set<Element>> edges = new HashMap<>();
    Set<Element> savedIn;
    Set<Element> savedOut;
    Set<Element> savedUsed;

    public RegisterAllocationVisitor(OllirResult ollirResult, String askedRegister) {
        this.ollirResult = ollirResult;
        this.askedRegister = Integer.parseInt(askedRegister);
        System.out.println("---------------------- Register Allocation ----------------------");
        for (Method method : ollirResult.getOllirClass().getMethods()) {

            method.buildCFG();
            if (method.getInstructions().get(0).getSuccessors() != null) {

                System.out.println("METHOD:\n");
                System.out.println(method.getMethodName());
                initializeVariables(method);
                buildOperands(method);
                System.out.println("OPERANDS"+this.operands);
                livelinessAnalysis(method);
                constructGraph(method);
            }

        }
    }

    public void initializeVariables(Method method) {
        for (Instruction instruction : method.getInstructions()) {

            this.in.put(instruction, new HashSet<>());
            this.out.put(instruction, new HashSet<>());
            this.used.put(instruction, new HashSet<>());
            this.defined.put(instruction, new HashSet<>());
            initializeDef(instruction, instruction);
            initializeUse(instruction, instruction);


        }

    }
    public void buildOperands(Method method) {
        for ( Set<Element> set: defined.values()){
            for (Element element : set){
                this.operands.add(element);
            }
        }


    }



    public void livelinessAnalysis(Method method) {
        boolean changed = true;

        while (changed) {
            changed = false;
            for (Instruction instruction : method.getInstructions()){

                savedOut = new HashSet<>(out.get(instruction));
                savedIn = new HashSet<>(in.get(instruction));
                for (Node successor : instruction.getSuccessors()) {
                    if (!successor.getClass().equals(Node.class)){
                        out.get(instruction).addAll(in.get(successor));
                    }
                }

                Set<Element> tempOut = new HashSet<>(savedOut);

                for (Element inst : defined.get(instruction)) {
                    for(Element inst2 : tempOut) {

                        if (inst.toString().equals(inst2.toString())) tempOut.remove(inst2);
                    }
                }

                in.get(instruction).addAll(tempOut);
                in.get(instruction).addAll(used.get(instruction));

                if (!((savedIn.equals(in.get(instruction)) && savedOut.equals(out.get(instruction))))) {
                    changed = true;
                }

            }


        }



    }


        public void initializeDef(Instruction instruction, Instruction instructionToAdd) {

            if (instructionToAdd.getInstType().equals(InstructionType.ASSIGN)){
                if (this.defined.containsKey(instruction)) defined.get(instructionToAdd).add(((AssignInstruction) instructionToAdd).getDest());
                else {
                    this.defined.put(instruction, new HashSet<>());
                    defined.get(instruction).add(((AssignInstruction) instructionToAdd).getDest());
                }
                initializeDef( instruction, ((AssignInstruction) instruction).getRhs());
                initializeUse(instruction,((AssignInstruction) instruction).getRhs());
            }


        }
        public void initializeUse(Instruction instruction, Instruction instructionToAdd) {

            if (instruction.getInstType().equals(InstructionType.RETURN)){
                if (((ReturnInstruction) instruction).getOperand() != null && !((ReturnInstruction) instruction).getOperand().isLiteral())
                    if (this.used.containsKey(instruction)) this.used.get(instruction).add(((ReturnInstruction) instruction).getOperand());
                    else {
                        this.used.put(instruction, new HashSet<>());
                        this.used.get(instruction).add(((ReturnInstruction) instruction).getOperand());
                    }
            }

            else  if (instructionToAdd.getInstType().equals(InstructionType.NOPER)){
                if ((((SingleOpInstruction) instructionToAdd).getSingleOperand()) != null && !(((SingleOpInstruction) instructionToAdd).getSingleOperand().isLiteral()))
                    if (this.used.containsKey(instruction)) this.used.get(instruction).add(((SingleOpInstruction) instructionToAdd).getSingleOperand());
                    else{

                        this.used.put(instruction, new HashSet<>());
                        this.used.get(instruction).add(((SingleOpInstruction) instructionToAdd).getSingleOperand());
                    }

            }
        }

        public void constructGraph(Method method) {

            for (Instruction instruction : method.getInstructions()) {
                for (Element element : defined.get(instruction)) {
                    for (Element element2 : out.get(instruction)) {
                        if (!(((Operand) element).getName().equals(((Operand) element2).getName()))) {
                            if (!edges.containsKey(element)) edges.put(element, new HashSet<>());
                            if (!edges.containsKey(element2)) edges.put(element2, new HashSet<>());
                            edges.get(element).add(element2);
                            edges.get(element2).add(element);
                        }

                    }
                }
            }

            System.out.println("GRAPH:");
            for (Element key : edges.keySet()) {
                System.out.println(key);
                System.out.println(edges.get(key));
            }


        }
}




