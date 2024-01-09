package pt.pa.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import pt.pa.dijkstra.DijkstraResult;
import pt.pa.graph.Edge;
import pt.pa.graph.Vertex;
import pt.pa.model.Route;
import pt.pa.model.Stop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a ticket with the simple format:
 * <p>
 * - Date, Origin, Destination;
 * - Distance, Duration, Number of stops.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class SimpleFormat implements Ticket {

    private PDDocument document;
    private PDPage page;
    private PDPageContentStream contentStream;
    private LocalDate currentDate;

    /**
     * Creates a SimpleFormat instance.
     *
     * @param result DijkstraResult the result
     * @param saveName String the name of the file to save
     */
    public SimpleFormat(DijkstraResult result, String saveName) {
        currentDate = LocalDate.now();
        document = new PDDocument();
        page = new PDPage();
        document.addPage(page);

        List<Vertex<Stop>> path = result.getPath();
        int size = path.size();

        String origin = path.get(0).element().getStopName();
        String destination = path.get(size - 1).element().getStopName();

        String blank = " ";

        String line1 = "+--------------------------------------------------------------------------------------------+";

        String line2 = String.format("+ %121s +", blank);

        String line3_1 = String.format("+ %3s Data: ", blank);
        String line3_2 = String.format("%10s Origem: ", blank);
        String line3_3 = String.format("%10s Destino: ", blank);
        String line3_4 = String.format("%16s +", blank);

        String line4 = String.format("+ %121s +", blank);

        String line5_1 = String.format("+ %3s Distância: ", blank);
        String line5_2 = String.format("%s km", blank);
        String line5_3 = String.format("%6s Duração: ", blank);
        String line5_4 = String.format("%8s Nº paragens: ", blank);
        String line5_5 = String.format("%24s +", blank);

        String line6 = String.format("+ %121s +", blank);

        String line7 = "+--------------------------------------------------------------------------------------------+";

        try {
            contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);
            contentStream.showText(line1);
            contentStream.newLine();
            contentStream.showText(line2);
            contentStream.newLine();
            contentStream.showText(line3_1 + currentDate + line3_2 + origin + line3_3 + destination + line3_4);
            contentStream.newLine();
            contentStream.showText(line4);
            contentStream.newLine();
            contentStream.showText(line5_1 + result.getCost() + line5_2 + line5_3 + getDuration(result) + line5_4 + getNumStops(result) + line5_5);
            contentStream.newLine();
            contentStream.showText(line6);
            contentStream.newLine();
            contentStream.showText(line7);
            contentStream.endText();
            contentStream.close();

            File file = new File(PATHNAME);
            file.mkdir();
            document.save(PATHNAME + saveName + ".pdf");
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDuration(DijkstraResult result) {
        int duration = 0;
        List<Edge<Route, Stop>> pathEdges = result.getPathEdges();

        for (Edge<Route, Stop> edge : pathEdges) {
            duration += edge.element().getDuration();
        }

        double aux = duration / 60;
        int hours = (int) Math.floor(aux);
        int minutes = duration % 60;

        return (hours + ":" + minutes);
    }

    /**
     * Auxiliary method to calculate and return the number of stops of a result.
     *
     * @param result an object of the DijkstraResult class
     * @return the number of stops
     */
    private int getNumStops(DijkstraResult result) {
        int numStops = 0;

        List<Vertex<Stop>> path = result.getPath();

        numStops = path.size() - 2;

        return numStops;
    }
}
