package homework4_2;
import java.util.List;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;

public class OutsideTermoMain {
	  public static final long SIM_PERIOD = 10;

	    public static void main(String[] args) {

	        Scenario sceonario = Scenario.extremeEvening();

	        Plant plant = new Plant(SIM_PERIOD, sceonario);

	        HeaterTankControllerComponent tankController = new HeaterTankControllerComponent(plant, SIM_PERIOD);

	        RoomTemperatureControllerComponent termostat = new RoomTemperatureControllerComponent(plant, SIM_PERIOD);

	        OutsideReferenceCalculatorComponent outTermo = new OutsideReferenceCalculatorComponent(plant, tankController, SIM_PERIOD);

	        termostat.start();

	        tankController.start();

	        plant.start();

	        outTermo.start();

	        // double waterRefTemp = 62.0;

	        double roomTemperature = 24.0;

	        for (int i = 0; i < sceonario.getScenarioLength(); i++) {

	            outTermo.setOutsideTemp(sceonario.getOutSideTemepratue(i));

	            tankController.setTankWaterTemp(plant.getTankWaterTemperature());

	            termostat.setInput(roomTemperature, plant.getRoomTemperature());

	            try {
	                Thread.sleep(10);
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	        tankController.stop();

	        termostat.stop();

	        outTermo.stop();

	        MainView windowTankController = FuzzyPVizualzer.visualize(tankController.getNet(), tankController.getRecorder());

	        MainView windowTermostat = FuzzyPVizualzer.visualize(termostat.getNet(), termostat.getRecorder());

	        MainView windowOutTermo = FuzzyPVizualzer.visualize(outTermo.getNet(), outTermo.getRecorder());

	        Plotter plotterTemperatureLog = new Plotter(plant.getTemeartureLogs());

	        Plotter plotterCommandLog = new Plotter(plant.getCommandLogs());

	        windowTankController.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());

	        windowTermostat.addInteractivePanel("TempLogs", plotterTemperatureLog.makeInteractivePlot());

	        windowTankController.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

	        windowTermostat.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

	        windowOutTermo.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

	        windowOutTermo.addInteractivePanel("ComandLogs", plotterCommandLog.makeInteractivePlot());

	        double[] tankTempStats = OutsideTermoMain.calcStatistics(plant.getTemeartureLogs().get("tankTemp"));

	        double[] rommTempStsats = OutsideTermoMain.calcStatistics(plant.getTemeartureLogs().get("roomTemp"));

	        System.out.println("max tank temp :" + tankTempStats[0]);

	        System.out.println("min tank temp :" + tankTempStats[1]);

	        System.out.println("avg tank temp :" + tankTempStats[2]);

	        System.out.println("max room temp :" + rommTempStsats[0]);

	        System.out.println("min room temp :" + rommTempStsats[1]);

	        System.out.println("avg room temp :" + rommTempStsats[2]);

	        System.out.println("heater on ratio:" + plant.heatingOnRatio());

	        System.out.println("max continous heating on:" + plant.maxContiniousHeaterOn());

	        System.out.println("all consunption ::" + plant.gasConsumption());

	        System.out.println("avg consunption in min ::" + plant.gasConsumption() / sceonario.getScenarioLength());
	    }
	    public static double[] calcStatistics(List<Double> list) {
	        double min = 1000.0;
	        double max = 0.0;
	        double sum = 0.0;

	        for (Double d : list) {
	            min = (min > d) ? d : min;
	            max = (max < d) ? d : max;
	            sum += d;
	        }
	        return new double[] { max, min, sum / list.size() };
	    }
}
